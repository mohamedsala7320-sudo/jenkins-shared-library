def call(String imageName, String imageTag, String deployFile) {
    echo 'Updating image in deployment file...'
    sh "sed -i 's|image: .*|image: ${imageName}:${imageTag}|' ${deployFile}"

    echo 'Deploying to Kubernetes...'
    sh "kubectl apply -f ${deployFile}"
}
