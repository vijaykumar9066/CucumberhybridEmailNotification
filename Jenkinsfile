pipeline {

    agent any

    environment {
        COMPOSE_PATH   = "${WORKSPACE}/docker"
        SELENIUM_GRID  = "true"
    }

    stages {

        // ------------------------------------------------
        // ✅ CHECKOUT
        // ------------------------------------------------
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/vijaykumar9066/CucumberhybridEmailNotification.git'
            }
        }

        // ------------------------------------------------
        // ✅ START SELENIUM GRID
        // ------------------------------------------------
        stage('Start Selenium Grid via Docker') {
            when {
                expression { return env.SELENIUM_GRID == "true" }
            }
            steps {
                echo 'Starting Selenium Grid with Docker Compose'
                bat "docker compose -f ${COMPOSE_PATH}\\docker-compose.yml up -d"

                echo "Waiting 30 seconds for Selenium Grid to be ready..."
                sleep 30
            }
        }

        // ------------------------------------------------
        // ✅ BUILD
        // ------------------------------------------------
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        // ------------------------------------------------
        // ✅ RUN TESTS
        // ------------------------------------------------
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        // ------------------------------------------------
        // ✅ CUCUMBER REPORT
        // ------------------------------------------------
        stage('Publish Cucumber Report') {
            steps {
                cucumber fileIncludePattern: '**/cucumber.html'
            }
        }

        // ------------------------------------------------
        // ✅ EXTENT REPORT
        // ------------------------------------------------
        stage('Publish Extent Report') {
            steps {
                publishHTML (
                    target: [
                        reportDir   : 'src/test/resources/ExtentReport',
                        reportFiles : 'ExtentReport.html',
                        reportName  : 'Extent Spark Report'
                    ]
                )
            }
        }

        // ------------------------------------------------
        // ✅ STOP SELENIUM GRID
        // ------------------------------------------------
        stage('Stop Selenium Grid') {
            when {
                expression { return env.SELENIUM_GRID == "true" }
            }
            steps {
                script {
                    echo "Stopping Selenium Grid..."
                    bat "docker compose -f ${COMPOSE_PATH}\\docker-compose.yml down"
                }
            }
        }
    }

    post {

        // ----------------------------------------------
        // ✅ ALWAYS: ARCHIVE REPORT + JUNIT XML
        // ----------------------------------------------
        always {
            archiveArtifacts artifacts: '**/src/test/resources/ExtentReport/*.html', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }

        // ----------------------------------------------
        // ✅ SUCCESS EMAIL
        // ----------------------------------------------
        success {
            emailext(
                to: 'vijakv155@gmail.com',
                subject: "✅ Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                body: """
<html>
<body>
<p>Hello Team,</p>

<p>Build details:</p>

<table border="1" cellpadding="5" cellspacing="0">
<tr><td><b>Project</b></td><td>${env.JOB_NAME}</td></tr>
<tr><td><b>Build Number</b></td><td>${env.BUILD_NUMBER}</td></tr>
<tr><td><b>Status</b></td><td>SUCCESS</td></tr>
<tr><td><b>Branch</b></td><td>${GIT_BRANCH}</td></tr>
<tr><td><b>Commit</b></td><td>${GIT_COMMIT}</td></tr>
<tr><td><b>URL</b></td><td><a href="${env.BUILD_URL}">${env.BUILD_URL}</a></td></tr>

<tr>
<td><b>Extent Report</b></td>
<td><a href="${env.BUILD_URL}Extent_Reports/index.html" target="_blank">Open Report</a></td>
</tr>
</table>

<p>Regards,<br>Automation Team</p>

</body>
</html>
"""
            )
        }

        // ----------------------------------------------
        // ❌ FAILURE EMAIL
        // ----------------------------------------------
        failure {
            emailext(
                to: 'vijakv155@gmail.com',
                subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                body: """
<html>
<body>
<p>Hello Team,</p>

<p>Build details:</p>

<table border="1" cellpadding="5" cellspacing="0">
<tr><td><b>Project</b></td><td>${env.JOB_NAME}</td></tr>
<tr><td><b>Build Number</b></td><td>${env.BUILD_NUMBER}</td></tr>
<tr><td><b>Status</b></td><td>FAILED</td></tr>
<tr><td><b>Branch</b></td><td>${GIT_BRANCH}</td></tr>
<tr><td><b>Commit</b></td><td>${GIT_COMMIT}</td></tr>
<tr><td><b>URL</b></td><td><a href="${env.BUILD_URL}">${env.BUILD_URL}</a></td></tr>

<tr>
<td><b>Extent Report</b></td>
<td><a href="${env.BUILD_URL}Extent_Reports/index.html" target="_blank">Open Report</a></td>
</tr>
</table>

<p>Regards,<br>Automation Team</p>

</body>
</html>
"""
            )
        }
    }
}


