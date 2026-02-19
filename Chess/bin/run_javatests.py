import subprocess

# Command to run your JUnit tests
cmd = [
    "java",
    "-cp",
    ".;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar",
    "org.junit.runner.JUnitCore",
    "chess.ChessGameTest"
]

# Run the command in the src directory
result = subprocess.run(cmd, cwd="C:/Users/Daikwon Raney/Downloads/ChessOOP-master/Chess/src", capture_output=True, text=True)

print("STDOUT:")
print(result.stdout)
print("STDERR:")
print(result.stderr)
