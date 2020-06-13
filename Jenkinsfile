pipeline {
    agent any

    stages {
        stage('Clone sources') {
            steps {
                git 'https://github.com/skjalal/javasparkexecutor'
            }
        }

        stage('Compile') {
            steps {
                sh 'gradle clean compileJava test'
            }
        }
    }
}