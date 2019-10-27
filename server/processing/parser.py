import datefinder


def find_time(text: str):
    matches = datefinder.find_dates(text)
    return matches
