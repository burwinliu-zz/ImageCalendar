from flask import Flask, request, jsonify
from processing.image import process_image

app = Flask(__name__)


@app.route('/get_event', methods=['GET'])
def hello():
    if request.method == 'GET':
        bucket_name = request.args.get('bucket')
        key = request.args.get('key')
        res = process_image(bucket_name, key)
        return jsonify(res)


if __name__ == '__main__':
    app.run(host= '0.0.0.0', port ='8080')
