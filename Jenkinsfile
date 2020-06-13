pipeline {
    agent any

    stages {
        stage('Clone sources') {
            steps {
                git url: 'https://github.com/skjalal/javasparkexecutor.git'
            }
        }

        stage('Gradle build') {
            steps {
                sh 'gradle build'
            }
        }
    }
}