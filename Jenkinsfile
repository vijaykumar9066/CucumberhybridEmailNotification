pipeline {

    agent any

    environment {
        COMPOSE_PATH   = "${WORKSPACE}/docker"
        SELENIUM_GRID  = "true"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/vijaykumar9066/CucumberhybridEmailNotification.git'
            }
        }

        stage('Start Selenium Grid via Docker') {
            when {
                expression { return env.SELENIUM_GRID == "true" }
            }
            steps {
                echo "Starting Selenium Grid..."
                bat "docker compose -f ${COMPOSE_PATH}\\docker-compose.yml up -d"

                echo "Waiting for Selenium Grid to be ready..."
                sleep 25
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Publish Cucumber Report') {
            steps {
                cucumber fileIncludePattern: '**/cucumber-report.html'
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML(
                    target: [
                        reportDir   : 'src/test/resources/ExtentReport',
                        reportFiles : 'ExtentReport.html',
                        reportName  : 'Extent Spark Report'
                    ]
                )
            }
        }

        stage('Stop Selenium Grid') {
            when {
                expression { return env.SELENIUM_GRID == "true" }
            }
            steps {
                echo "Stopping Selenium Grid..."
                bat "docker compose -f ${COMPOSE_PATH}\\docker-compose.yml down"
            }
        }
    }

    post {

        always {
            archiveArtifacts artifacts: '**/src/test/resources/ExtentReport/*.html', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }

        success {
            emailext(
                to: 'vijakv155@gmail.com',
                subject: "✅ Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                body: "<html><body>Build Success</body></html>"
            )
        }

        failure {
            emailext(
                to: 'vijakv155@gmail.com',
                subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                body: "<html><body>Build Failed</body></html>"
            )
        }
    }
}
