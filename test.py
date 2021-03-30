import os
import jnius_config

from concurrent.futures import ThreadPoolExecutor

if not jnius_config.vm_running:
    jar_path = os.path.realpath(os.path.join(os.path.dirname(__file__), 'target', 'pyjnius-multithreading-java-0.0.1.jar'))
    if jar_path not in jnius_config.get_classpath():
        jnius_config.add_classpath(jar_path)

from jnius import autoclass
PyJniusExample = autoclass('aoka.sample.PyJniusExample')

def run(tid):
    global PyJniusExample
    instance = PyJniusExample(tid)
    _ = instance.process(tid)
    for _ in range(10000):
        instance.validate(tid)

def main():
    for i in range(500):
        if i % 10 == 0:
            print("iterate", i)
        with ThreadPoolExecutor(16) as e:
            futures = [e.submit(run, i+1) for i in range(16)]
        result = [f.result() for f in futures]
    print('The process has finished without any errors.  Please try again.')

if __name__ == "__main__":
    main()
