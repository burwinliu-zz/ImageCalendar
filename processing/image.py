import boto3
import os
from definition import ROOT_DIR
from dotenv import load_dotenv
import base64
from processing.parser import find_time
from flask import jsonify

load_dotenv(dotenv_path=os.path.join(ROOT_DIR, ".env"))


def encode_img(path: str):
    with open(path, "rb") as image_file:
        encoded_string = base64.b64encode(image_file.read())
        return encoded_string


def upload_image(path: str, bucket: str, key: str):
    s3 = boto3.client('s3',
                      aws_access_key_id=os.getenv('AWS_ID'),
                      aws_secret_access_key=os.getenv('AWS_SECRET'), )
    s3.upload_file(path, bucket, key)


def process_image(bucket: str, key: str):
    client = boto3.client('rekognition',
                          region_name='us-west-2',
                          aws_access_key_id=os.getenv('AWS_ID'),
                          aws_secret_access_key=os.getenv('AWS_SECRET'), )
    response = client.detect_text(Image={'S3Object': {'Bucket': bucket, 'Name': key}}, )

    res = {'title': _find_largest_text(response)[0],
           'description': _find_largest_text(response)[1],
           'times': _find_time(response)}
    return res


def _find_largest_text(response: dict):
    # True is for parent id, false for id number
    to_look = sorted(response['TextDetections'], key=lambda x: x['Geometry']['BoundingBox']['Height'], reverse=True)
    large = round(to_look[0]['Geometry']['BoundingBox']['Height'], 1)
    res, blurb = "", list()
    for text in to_look:
        if "ParentId" not in text:
            if round(text['Geometry']['BoundingBox']['Height'], 1) == large:
                res += f"{text['DetectedText']} "
            else:
                blurb.append(f"{text['DetectedText']}")
    return res, blurb


def _find_time_aws(text: str):
    '''
    next iteration, first get working

    :param text:
    :return:
    '''
    comprehend = boto3.client(service_name='comprehend',
                              region_name='us-west-2',
                              aws_access_key_id=os.getenv('AWS_ID'),
                              aws_secret_access_key=os.getenv('AWS_SECRET'), )
    response = comprehend.detect_entities(Text=text, LanguageCode='en')
    return response


def _find_time(response: dict):
    to_parse = ""
    for text in response['TextDetections']:
        to_parse += f" {text['DetectedText']}"
    return [i for i in find_time(to_parse)]


if __name__ == "__main__":
    # Change bucket and photo to your S3 Bucket and image.
    img_bucket = 'imagecalendarwest2'
    img_key = 'test'
    # upload_image(os.path.join(ROOT_DIR, "flyerexamples\\trial4.jpg"), img_bucket, img_key)
    print(process_image(img_bucket, img_key))