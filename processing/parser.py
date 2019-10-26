import datefinder


def find_time(text: str):
    matches = datefinder.find_dates(text)
    for match in matches:
        print(match)
    return match
