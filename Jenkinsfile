pipeline {
    agent any

    tools {
        maven "3.8.6"
    }

    stages {
        stage('Build') {
            steps {
                echo 'Build process was started'
                bat "mvn test"
                echo 'Build process was ended'
            }
        }
//         stage('Results') {
//             steps {
//                 echo 'Results process was started'
//                 bat "mvn allure:serve"
//                 echo 'Results process was ended'
//             }
//         }
    }
    post {
        always {
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'allure-results']],
            ])
        }
    }
}