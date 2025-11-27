#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    pid_t pid = fork();

    if (pid < 0) {
        perror("fork failed");
        return 1;
    } 
    else if (pid == 0) {
        // Child process runs "ls"
        execl("/bin/ls", "ls", NULL);
        perror("execl failed");  // only prints if exec fails
    } 
    else {
        // Parent process waits for child
        wait(NULL);
        printf("Child process finished.\n");
    }

    return 0;
}

