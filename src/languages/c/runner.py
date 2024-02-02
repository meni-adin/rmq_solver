
import io
import os
import subprocess
import pathlib

WORKING_DIR_PATH = pathlib.Path(__file__).parent.resolve()
SRC_DIR_NAME     = 'src'
SRC_DIR_PATH     = os.path.join(WORKING_DIR_PATH, SRC_DIR_NAME)
BUILD_DIR_NAME   = 'build'
BUILD_DIR_PATH   = os.path.join(WORKING_DIR_PATH, BUILD_DIR_NAME)
APP_DIR_NAME     = 'app'
APP_DIR_PATH     = os.path.join(BUILD_DIR_PATH, APP_DIR_NAME)
EXE_FILE_NAME    = 'app'
EXE_FILE_PATH    = os.path.join(APP_DIR_PATH, EXE_FILE_NAME)


def run(logger, args):
    global g_logger
    g_logger = logger
    run_configuration()
    run_build()
    execute()

def run_configuration():
    g_logger.info("Starting CMake configuration...")
    subprocess.check_call(["cmake", "-S", SRC_DIR_PATH, "-B", BUILD_DIR_PATH])
    g_logger.info("CMake configuration ended successfully")

def run_build():
    g_logger.info("Starting CMake build...")
    subprocess.check_call(["cmake", "--build", BUILD_DIR_PATH])
    g_logger.info("CMake build ended successfully")

def execute():
    g_logger.info("Starting code execution...")
    subprocess.check_call([os.path.join(".", EXE_FILE_PATH)], stdout=subprocess.PIPE,stderr=subprocess.STDOUT)
    g_logger.info("Code execution ended successfully")
