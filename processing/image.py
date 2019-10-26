import boto3
import os
from os import getenv
from definition import ROOT_DIR
from dotenv import load_dotenv
import base64
import parser


load_dotenv(dotenv_path=os.path.join(ROOT_DIR, ".env"))


def encode_img(path: str):
    with open(path, "rb") as image_file:
        encoded_string = base64.b64encode(image_file.read())
        return encoded_string


def upload_image(path: str, bucket: str, key: str):
    s3 = boto3.client('s3',
                      aws_access_key_id=os.getenv('AWS_ID'),
                      aws_secret_access_key=os.getenv('AWS_SECRET'),)
    s3.upload_file(path, bucket, key)


if __name__ == "__main__":
    client = boto3.client('rekognition',
                          region_name='us-west-1',
                          aws_access_key_id=os.getenv('AWS_ID'),
                          aws_secret_access_key=os.getenv('AWS_SECRET'), )
    # Change bucket and photo to your S3 Bucket and image.
    img_bucket = 'imagecalendar'
    img_key = 'test'
    upload_image(os.path.join(ROOT_DIR, "flyerexamples\\trial.jpg"), img_bucket, img_key)
    response = client.detect_text(Image={'S3Object': {'Bucket': img_bucket, 'Name': img_key}},)

    text = ""
    for text in response['TextDetections']:
        print('Detected text:' + text['DetectedText'])
        print('Confidence: ' + "{:.2f}".format(text['Confidence']) + "%")
        print('Id: {}'.format(text['Id']))
        if 'ParentId' in text:
            print('Parent Id: {}'.format(text['ParentId']))
        print('Type:' + text['Type'])
        print()

    parser.find_time(text)


