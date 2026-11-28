def call(String imageName, String imageTag, String branch = 'lab23') {
    echo 'Updating image in deployment file...'
    sh "sed -i 's|image: .*|image: ${imageName}:${imageTag}|' k8s/deployment.yaml"
    sh 'grep image: k8s/deployment.yaml'

    echo 'Pushing updated manifest to GitHub (Argo CD will sync it)...'
    withCredentials([usernamePassword(credentialsId: 'github-creds',
                                      usernameVariable: 'GIT_USER',
                                      passwordVariable: 'GIT_TOKEN')]) {
        sh 'git config user.email "jenkins@ivolve.local"'
        sh 'git config user.name "jenkins"'
        sh 'git add k8s/'
        sh "git commit -m 'ci: update image to ${imageName}:${imageTag}' || echo 'nothing to commit'"
        sh '''
            git push https://${GIT_USER}:${GIT_TOKEN}@github.com/mohamedsala7320-sudo/DemoApp.git HEAD:refs/heads/''' + branch
    }
}
