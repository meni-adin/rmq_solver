import filecmp
import importlib
import logging
import os
import pathlib

APP_NAME             = "rmq-solver"
WORKING_DIR          = pathlib.Path(__file__).parent.resolve()
MODULE_FILE_NAME     = "runner"
INVOCATION_FUNC_NAME = "run"
LOG_PATH             = "myapp.log"
PROG_LANGUAGES = [
    # "c",
    "java",
]
EXPECTED_OUTPUT_FILES = [f"RMQ{num}.txt" for num in range(1, 5)]

class CustomFormatter(logging.Formatter):

    grey = "\x1b[38;20m"
    yellow = "\x1b[33;20m"
    red = "\x1b[31;20m"
    bold_red = "\x1b[31;1m"
    light_purple = "\x1b[38;5;105m"
    reset = "\x1b[0m"
    format = "%(asctime)s.%(msecs)03d %(name)-20s %(levelname)-8s %(message)s"

    FORMATS = {
        logging.DEBUG: grey + format + reset,
        logging.INFO: light_purple + format + reset,
        logging.WARNING: yellow + format + reset,
        logging.ERROR: red + format + reset,
        logging.CRITICAL: bold_red + format + reset
    }

    def format(self, record):
        log_fmt = self.FORMATS.get(record.levelno)
        formatter = logging.Formatter(log_fmt, datefmt="%H:%M:%S")
        return formatter.format(record)

def run():
    configure_logging()
    print_header()
    for lang in PROG_LANGUAGES:
        run_solver(lang)
        verify_output(lang)

def configure_logging():
    logging.basicConfig(level=logging.DEBUG,
                        format="%(asctime)s.%(msecs)03d %(name)-20s %(levelname)-8s %(message)s",
                        datefmt="%Y-%m-%d %H:%M",
                        filename=LOG_PATH,
                        filemode="w")
    console = logging.StreamHandler()
    console.setLevel(logging.DEBUG)
    formatter = CustomFormatter()
    console.setFormatter(formatter)
    logging.getLogger().addHandler(console)

def print_header():
    logging.info("--- RMQ Solver ---")

def run_solver(lang):
    args = ["-i", "inputDir"]
    module = importlib.import_module(f"{lang}.{MODULE_FILE_NAME}")
    logger = logging.getLogger(f"{APP_NAME}.{lang}")
    logging.info(f"Calling {lang.capitalize()} runner...")
    exec(f"module.{INVOCATION_FUNC_NAME}(logger, args)")
    logging.info(f"{lang.capitalize()} runner ended successfully")


def verify_output(lang):
    logging.info(f"Verifying output...")
    output_path = f"{WORKING_DIR}/{lang}/output"
    if not os.path.isdir(output_path):
        logging.error(f"{output_path} is missing")
        exit(1)

    output_files = [f for f in os.listdir(output_path) if os.path.isfile(os.path.join(output_path, f))]
    for file in EXPECTED_OUTPUT_FILES:
        if file not in output_files:
            logging.error(f"{file} is missing from output")
            exit(1)
    trusted_solution = EXPECTED_OUTPUT_FILES[0]
    for file in (set(EXPECTED_OUTPUT_FILES) - set([trusted_solution])):
        if not filecmp.cmp(f"{output_path}/{trusted_solution}", f"{output_path}/{file}", shallow=False):
            logging.error(f"the solution in {output_path}/{file} is wrong")
    logging.info(f"Output verified successfully")


if __name__ == "__main__":
    run()
