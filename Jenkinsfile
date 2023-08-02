pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'docker build -t yamiannephilim/ecommerce:latest .'
            }
        }

        stage('Pushing') {
            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh 'docker push yamiannephilim/ecommerce'
                }
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
                sh 'docker container stop ecommerce || echo "this container does not exist"'
                sh 'docker network create yan || echo "this network exist"'
                sh 'echo y | docker container prune'
                sh 'docker run --name ecommerce --network yan --restart=unless-stopped -d yamiannephilim/ecommerce:latest'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
