pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Pull the latest code from the Git repository
                git url: 'https://github.com/AbhishekBawa25/ChatgptTest.git', branch: 'main'
            }
        }
        stage('Set Up Selenium Grid') {
            steps {
                // Start Docker Compose in detached mode
                sh 'docker-compose up -d'
            }
        }
        stage('Run Tests') {
            steps {
                // Run Maven tests
                sh 'mvn clean test'
            }
        }
        stage('Tear Down Selenium Grid') {
            steps {
                // Stop and remove Docker Compose containers
                sh 'docker-compose down'
            }
        }
    }
    post {
        always {
            // Archive test reports (optional)
            archiveArtifacts artifacts: 'target/surefire-reports/*.xml', allowEmptyArchive: true
        }
        failure {
            // Notify on failure (optional, e.g., print to console)
            echo 'Tests failed!'
        }
        success {
            // Notify on success (optional)
            echo 'Tests passed successfully!'
        }
    }
}



