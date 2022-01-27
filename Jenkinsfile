pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: git
    image: alpine/git
    tty: true
    command: ["cat"]
    env:
    - name: PROJECT_URL
      value: https://github.com/gui549/knote.git
  - name: docker
    image: docker
    tty: true
    command: ["cat"]
    env:
    - name: PROJECT_NAME
      value: knote
    volumeMounts:
      - mountPath: /var/run/docker.sock
        name: docker-socket
  - name: java
    image: openjdk:17-alpine
    tty: true
    command: ["cat"]
    env:
    - name: PROJECT_NAME
      value: knote
  volumes:
  - name: docker-socket
    hostPath:
      path: /var/run/docker.sock
'''
            defaultContainer 'docker'
        }
    }
    stages {
        stage('Checkout') {
            steps {
                container('git') {
                    sh """
                    git clone \$PROJECT_URL
                    """
                }
            }
        }
        stage('Build') {
            steps {
                container('java') {
                    sh """
                    cd \$PROJECT_NAME
                    chmod +x gradlew
                    ./gradlew build
                    """
                }
                container('docker') {
                    sh """
                    cd \$PROJECT_NAME
                    docker build -t \$PROJECT_NAME .
                    """
                }
            }
        }
        stage('Test') {
            steps {
                sh "echo Test"
            }
        }
        stage('Push') {
            environment {
                ECR_REPOSITORY = '567232876231.dkr.ecr.ap-northeast-3.amazonaws.com/knote:latest'
            }

            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'account-ECR', usernameVariable: 'ECR_USER', passwordVariable: 'ECR_PASSWORD']]) {
                    sh """
                    docker login --username ${ECR_USER} --password ${ECR_PASSWORD} 567232876231.dkr.ecr.ap-northeast-3.amazonaws.com
                    docker tag \$PROJECT_NAME:latest $ECR_REPOSITORY
                    docker push $ECR_REPOSITORY
                    """
                }
            }
        }
    }
}