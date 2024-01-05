
import logging
import os
import subprocess
import pathlib

WORKING_DIR = pathlib.Path(__file__).parent.resolve()
SRC_DIR = "src"
BUILD_DIR = "build"
EXEC_NAME = "app"

def run(logger, args):
    global g_logger
    g_logger = logger
    run_configuration()
    run_build()
    execute()

def run_configuration():
    g_logger.info("Starting CMake configuration...")
    subprocess.run(["cmake", "-S", os.path.join(WORKING_DIR, SRC_DIR), "-B", os.path.join(WORKING_DIR, BUILD_DIR)])
    g_logger.info("CMake configuration ended successfully")

def run_build():
    g_logger.info("Starting CMake build...")
    subprocess.run(["cmake", "--build", os.path.join(WORKING_DIR, BUILD_DIR)])
    g_logger.info("CMake build ended successfully")

def execute():
    g_logger.info("Starting code execution...")
    subprocess.run([os.path.join(".", WORKING_DIR, BUILD_DIR, EXEC_NAME, EXEC_NAME)])
    g_logger.info("Code execution ended successfully")
