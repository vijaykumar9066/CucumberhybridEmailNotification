pipeline {

    agent any



    tools {

        maven 'maven-3.9.9'

    }



    stages {

        

        stage('Checkout') {

            steps {

                git branch: 'main', url: 'https://github.com/vijaykumar9066/CucumberhybridEmailNotification.git'

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

                cucumber fileIncludePattern: '**/cucumber.json'

            }

        }



        stage('Publish Extent Report') {

            steps {

                publishHTML (target: [

                    reportDir   : 'src/test/resources/ExtentReport',

                    reportFiles : 'ExtentReport.html',

                    reportName  : 'Extent Spark Report'

                ])

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

                subject: " Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",

                body: """

                <html>

                <body>

                <p>Hello Team,</p>

                <p>The latest Jenkins build has completed successfully </p>



                <p><b>Project Name:</b> ${env.JOB_NAME}</p>

                <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>

                <p><b>Build Status:</b> <span style="color:green;"><b>SUCCESS</b></span></p>

                <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>



                <p><b>Last Commit:</b> ${env.GIT_COMMIT}</p>

                <p><b>Branch:</b> ${env.GIT_BRANCH}</p>



                <p><b>Extent Report:</b> 

                   <a href="${env.BUILD_URL}HTML_29EXTENT_20Report/"> CLICK HERE </a>

                </p>



                <p>Regards,<br>

                <b>Automation Team</b></p>

                </body>

                </html>

                """,

                mimeType: 'text/html',

                attachLog: true

            )

        }



        failure {

            emailext(

                to: 'vijakv155@gmail.com',

                subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",

                body: """

                <html>

                <body>

                <p>Hello Team,</p>

                <p>The latest Jenkins build has <b style="color:red;">FAILED</b> ❌</p>



                <p><b>Project Name:</b> ${env.JOB_NAME}</p>

                <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>

                <p><b>Build Status:</b> <span style="color:red;"><b>FAILED</b></span></p>

                <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>



                <p><b>Last Commit:</b> ${env.GIT_COMMIT}</p>

                <p><b>Branch:</b> ${env.GIT_BRANCH}</p>



                <p><b>Extent Report:</b> 

                    <a href="${env.BUILD_URL}HTML_29EXTENT_20Report/"> CLICK HERE </a>

                </p>



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