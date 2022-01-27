pipeline {
    agent {
        kubernetes {
            yaml """
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
      - mountPath: /root/.docker
        name: docker-config
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
  - name: docker-config
    configMap:
      name: docker-config
"""
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
                PATH = "/root/bin:$PATH"
                ECR_REPOSITORY = '567232876231.dkr.ecr.ap-northeast-3.amazonaws.com/knote:latest'
            }

            steps {
                withCredentials([aws(credentialsId: 'kong-jenkins-credentials',
                                    accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                                    secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']) {
                    sh """
                    ls ~/.docker
                    wget https://amazon-ecr-credential-helper-releases.s3.us-east-2.amazonaws.com/0.6.0/linux-amd64/docker-credential-ecr-login
                    chmod +x docker-credential-ecr-login
                    mkdir ~/bin
                    mv docker-credential-ecr-login ~/bin/docker-credential-ecr-login
                    docker tag \$PROJECT_NAME:latest $ECR_REPOSITORY
                    docker push $ECR_REPOSITORY
                    """
                }
            }
        }
    }
}