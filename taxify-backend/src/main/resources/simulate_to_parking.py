import json
import time

from locust import events, HttpUser, task
from urllib3 import PoolManager

VEHICLE_ID = ""
START = []


@events.init_command_line_parser.add_listener
def _(parser):
    parser.add_argument("--data", type=json.loads)


@events.test_start.add_listener
def _(environment, **kw):
    data = environment.parsed_options.data
    global VEHICLE_ID, START
    VEHICLE_ID = data["id"]
    START = [data["start"]["longitude"], data["start"]["latitude"]]


class SimulateToParking(HttpUser):
    fixed_count = 1
    pool_manager = PoolManager(maxsize=1, block=True)

    @task
    def set_location(self):
        global START
        location = "{\"longitude\"%3A" + str(START[0]) + "%2C\"latitude\"%3A" + str(START[1]) + "}"
        response = self.client.get("/parking/closest?location=" + location)
        closest_parking = response.json()
        parking_location = [closest_parking["location"]["latitude"], closest_parking["location"]["longitude"]]
        url = "https://api.openrouteservice.org/v2/directions/driving-car"
        url += "?api_key=5b3ce3597851110001cf6248e39388eebabc4b62a4c73f8387f68638"
        url += "&start="
        url += f"{START[0]},{START[1]}"
        url += "&end="
        url += f"{parking_location[0]},{parking_location[1]}"
        response = self.client.get(url)
        route = [START, *response.json()["features"][0]["geometry"]["coordinates"], parking_location]

        for waypoint in route:
            longitude = waypoint[0]
            latitude = waypoint[1]
            location = {"longitude": longitude, "latitude": latitude}
            request_body = {
                "id": VEHICLE_ID,
                "location": location
            }
            self.client.put("/vehicle/location", json=request_body)
            time.sleep(2)
        self.environment.runner.quit()
