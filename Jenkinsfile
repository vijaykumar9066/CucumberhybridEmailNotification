pipeline {

    agent any                      // Runs pipeline on any available Jenkins agent/node

    /*
    tools {                        // (COMMENTED) — Jenkins managed tools such as Maven
        maven 'maven-3.9.9'        // Name must match Maven config under Global Tool
    }
    */

    stages {

        // ------------------------------
        // ✅ 1) CHECKOUT CODE
        // ------------------------------
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/vijaykumar9066/CucumberhybridEmailNotification.git'
            }
        }

        // ------------------------------
        // ✅ 2) BUILD WITHOUT TESTS
        // ------------------------------
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        // ------------------------------
        // ✅ 3) RUN TESTS
        // ------------------------------
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        // ------------------------------
        // ✅ 4) PUBLISH CUCUMBER REPORT
        // ------------------------------
        stage('Publish Cucumber Report') {
            steps {
                cucumber fileIncludePattern: '**/cucumber.json'
            }
        }

        // ------------------------------
        // ✅ 5) PUBLISH EXTENT REPORT
        // ------------------------------
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
    }

    post {

        // ---------------------------------------------------------
        // ✅ ALWAYS RUN — ARCHIVE REPORTS
        // ---------------------------------------------------------
        always {
            archiveArtifacts artifacts: '**/src/test/resources/ExtentReport/*.html', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }

        // ---------------------------------------------------------
        // ✅ SUCCESS EMAIL
        // ---------------------------------------------------------
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

<!-- ✅ Extent Report Link -->
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

        // ---------------------------------------------------------
        // ❌ FAILURE EMAIL
        // ---------------------------------------------------------
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

<!-- ✅ Extent Report Link -->
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

