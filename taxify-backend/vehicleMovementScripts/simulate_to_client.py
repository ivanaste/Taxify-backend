import json
import time

from locust import events, HttpUser, task
from urllib3 import PoolManager

VEHICLE_ID = ""
START = []
END = []

"""
locust -f vehicleMovementScripts/simulate_to_client.py --conf vehicleMovementScripts/locust.conf --data '{"id":"08a26db0-21f1-4641-b74c-4ea70a536494","start":{"longitude":19.837602745318073,"latitude":45.24064981289879},"end":{"longitude":19.84326,"latitude":45.24328}}'
"""

@events.init_command_line_parser.add_listener
def _(parser):
    parser.add_argument("--data", type=json.loads)


@events.test_start.add_listener
def _(environment, **kw):
    data = environment.parsed_options.data
    global VEHICLE_ID, START, END
    VEHICLE_ID = data["id"]
    START = [data["start"]["longitude"], data["start"]["latitude"]]
    END = [data["end"]["longitude"], data["end"]["latitude"]]


class SimulateToClient(HttpUser):
    fixed_count = 1
    pool_manager = PoolManager(maxsize=1, block=True)

    @task
    def set_location(self):
        global START, END
        url = "https://api.openrouteservice.org/v2/directions/driving-car"
        url += "?api_key=5b3ce3597851110001cf6248e39388eebabc4b62a4c73f8387f68638"
        url += "&start="
        url += f"{START[0]},{START[1]}"
        url += "&end="
        url += f"{END[0]},{END[1]}"
        response = self.client.get(url)
        print(response)
        route = [START, *response.json()["features"][0]["geometry"]["coordinates"], END]

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