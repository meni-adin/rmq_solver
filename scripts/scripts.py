import argparse
import subprocess
import pathlib

WORKING_DIR_PATH = pathlib.Path(__file__).parent.resolve()

def cmd_build_image():
    subprocess.run(['docker', 'build', '-t', 'rmq-solver-img', '.'])

def cmd_run_dev_cont():
    subprocess.run(['docker', 'run', '--name', 'rmq-solver-cont', '-it', '--rm', 'rmq-solver-img', 'bash'])

def cmd_update_cont_files():
    subprocess.run(['docker', 'exec', 'rmq-solver-cont', 'rm', '-rf', '/root/src'])
    subprocess.run(['docker', 'cp', f'{WORKING_DIR_PATH}/../src', 'rmq-solver-cont:/root/src/'])

SCRIPTS_MAP = {
    'build-image': cmd_build_image,
    'run-dev-cont': cmd_run_dev_cont,
    'update-cont-files': cmd_update_cont_files,
}

SCRIPT_CHOICES = [script for script in SCRIPTS_MAP]

def run():
    args = parse_args()
    run_command(args)

def parse_args():
    parser = argparse.ArgumentParser(
        prog='scripts',
        description='Cross-platform script runner')

    parser.add_argument('-s', '--script',
                        action='store',
                        required=True,
                        choices=SCRIPT_CHOICES
                        )
    return parser.parse_args()

def run_command(args):
    SCRIPTS_MAP[args.script]()


if __name__ == "__main__":
    run()
