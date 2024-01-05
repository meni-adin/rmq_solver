
import os
import subprocess
import pathlib

WORKING_DIR = pathlib.Path(__file__).parent.resolve()
SRC_DIR = "src"
BUILD_DIR = "build"
EXEC_NAME = "app"

def run(logger, args):
    global LOGGER
    LOGGER = logger
    logger.info("-- Java Runner --")
    os.chdir(WORKING_DIR)
    build()
    execute()

def build():
    LOGGER.info("Building .jar file...")
    subprocess.run(["pwd"])
    subprocess.run(["gradle", "build"])
    LOGGER.info(".jar file built successfully")

def execute():
    LOGGER.info("Executing .jar file...")
    subprocess.run(["java", "-jar", "build/libs/rmq_solver.jar", "input/01_data.txt", "input/01_queries.txt", "output"])
    LOGGER.info(".jar file executed successfully")

if __name__ == "__main__":
    run()
