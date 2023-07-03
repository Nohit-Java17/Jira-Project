pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'docker build -t ecommerce:latest .'
            }
        }

        stage('Cleaning') {
            steps {
                script {
                    def containerId = sh(returnStdout: true, script: 'docker ps -aqf "name=ecommerce"').trim()
                    if (containerId) {
                        sh "docker stop $containerId"
                        sh "docker rm $containerId"
                    }
                }
            }
        }

        stage('Run') {
            steps {
                sh 'docker run --name ecommerce --network yan --restart=unless-stopped -d ecommerce:latest'
            }
        }
    }
}
