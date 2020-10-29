pipeline {
    agent any

    stages {
        stage('Init') {
            steps {
                cleanWs()
            }
        }
       stage('Checkout') {
           steps {
               git credentialsId: 'GitHubUserID', url: 'https://github.com/skjalal/javasparkexecutor.git'
               script{
                    env.GIT_COMMIT = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
               }
               githubNotify account: 'skjalal', context: 'continuous-integration/jenkins', credentialsId: 'GitHubUserID', description: 'This is Simple Build Notify', sha: "${GIT_COMMIT}", repo: 'javasparkexecutor', status: 'PENDING'
               sh 'chmod +x /var/jenkins_home/workspace/javasparkexecutor/gradlew'
           }
       }
       stage('Build') {
           steps {
               echo 'Build Gradle Project'
               sh '/var/jenkins_home/workspace/javasparkexecutor/gradlew clean classes'
           }
       }
    }
    post {
        success {
            echo 'Build Succeeded'
            githubNotify account: 'skjalal', context: 'continuous-integration/jenkins', credentialsId: 'GitHubUserID', description: 'This is Simple Build Notify', sha: "${GIT_COMMIT}", repo: 'javasparkexecutor', status: 'SUCCESS'
        }
        failure {
            echo 'Build was Failed'
            githubNotify account: 'skjalal', context: 'continuous-integration/jenkins', credentialsId: 'GitHubUserID', description: 'This is Simple Build Notify', sha: "${GIT_COMMIT}", repo: 'javasparkexecutor', status: 'FAILURE'
        }
    }
 }
