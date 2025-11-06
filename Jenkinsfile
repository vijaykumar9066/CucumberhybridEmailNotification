pipeline {

    agent any                      // Runs pipeline on any available Jenkins agent/node


    /*  
    tools {                        // (COMMENTED) — specifies Jenkins-managed tools such as Maven
        maven 'maven-3.9.9'        // Name must match Maven name configured in Jenkins Global Tools
    } 
    */

    stages {

        // ------------------------------
        // ✅ 1) CHECKOUT SOURCE CODE
        // ------------------------------
        stage('Checkout') {
            steps {
                git branch: 'main',    // Checkout the "main" branch
                    url: 'https://github.com/vijaykumar9066/CucumberhybridEmailNotification.git'   // Repo URL
            }
        }

        // ------------------------------
        // ✅ 2) BUILD JAR WITHOUT TESTS
        // ------------------------------
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'   // On Windows — Build the project but skip tests
            }
        }

        // ------------------------------
        // ✅ 3) EXECUTE TESTS
        // ------------------------------
        stage('Test') {
            steps {
                bat 'mvn test'      // Runs TestNG/Cucumber tests
            }
        }

        // ------------------------------
        // ✅ 4) PUBLISH CUCUMBER REPORT
        // ------------------------------
        stage('Publish Cucumber Report') {
            steps {
                cucumber fileIncludePattern: '**/cucumber.json'   // Publish cucumber JSON report
            }
        }

        // ------------------------------
        // ✅ 5) PUBLISH EXTENT REPORT (HTML)
        // ------------------------------
        stage('Publish Extent Report') {
            steps {
                publishHTML (
                    target: [
                        reportDir   : 'src/test/resources/ExtentReport',   // Folder where report exists
                        reportFiles : 'ExtentReport.html',                 // Report file
                        reportName  : 'Extent Spark Report'                // Display name in Jenkins
                    ]
                )
            }
        }
    }

    post {

        // ---------------------------------------------------------
        // ✅ ACTIONS AFTER EVERY BUILD (SUCCESS OR FAILURE)
        // ---------------------------------------------------------
        always {
            archiveArtifacts artifacts: '**/src/test/resources/ExtentReport/*.html', fingerprint: true   // Save reports
            junit 'target/surefire-reports/*.xml'              // Publish JUnit test results
        }

        // ---------------------------------------------------------
        // ✅ EMAIL ON SUCCESS
        // ---------------------------------------------------------
        success {
            emailext(
                to: 'vijakv155@gmail.com',                        // Receiver
                subject: "✅ Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",   // Email Subject
                body: """
                <html>
                <body>
                <p>Hello Team,</p>
                <p>The latest Jenkins build has completed successfully ✅</p>

                <p><b>Project Name:</b> ${env.JOB_NAME}</p>              <!-- Jenkins Job Name -->
                <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>         <!-- Build number -->
                <p><b>Build Status:</b> SUCCESS</p>
                <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>   <!-- Link to Jenkins build -->

                <p>Regards,<br>
                <b>Automation Team</b></p>
                </body>
                </html>
                """,
                mimeType: 'text/html',          // Tells email is HTML format
                attachLog: true                 // Attach console log in email
            )
        }

        // ---------------------------------------------------------
        // ❌ EMAIL ON FAILURE
        // ---------------------------------------------------------
        failure {
            emailext(
                to: 'vijakv155@gmail.com',
                subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <html>
                <body>
                <p>Hello Team,</p>
                <p>The latest Jenkins build has <b style="color:red">FAILED ❌</b></p>

                <p><b>Project Name:</b> ${env.JOB_NAME}</p>
                <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                <p><b>Build Status:</b> FAILED</p>
                <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>

                <p>Regards,<br>
                <b>Automation Team</b></p>
                </body>
                </html>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}
