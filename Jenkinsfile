pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                sh 'gradle clean compileJava test'
            }
        }
    }
}