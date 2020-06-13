pipeline {
    agent any

    stages {
        stage('Clone sources') {
            git url: 'https://github.com/skjalal/javasparkexecutor.git'
        }

        stage('Gradle build') {
            sh 'gradle build'
        }
    }
}