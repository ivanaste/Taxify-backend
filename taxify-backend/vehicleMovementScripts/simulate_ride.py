import json
import time

from locust import events, HttpUser, task
from urllib3 import PoolManager

VEHICLE_ID = ""
ROUTE = []
FOLLOW_ROUTE = None

""" 
PRIMER KOMANDE ZA POKRETANJE:
locust -f vehicleMovementScripts/simulate_ride.py --conf locust.conf --data '{"id": "08a26db0-21f1-4641-b74c-4ea70a536494","waypoints": [{"longitude": 19.83879, "latitude": 45.23814, "isStop": false},{"longitude": 19.83892, "latitude": 45.2379, "isStop": false},{"longitude": 19.839231, "latitude": 45.237828, "isStop": false},{"longitude": 19.839405, "latitude": 45.237483, "isStop": false},{"longitude": 19.83957, "latitude": 45.237116, "isStop": false},{"longitude": 19.839739, "latitude":45.236798, "isStop": false},{"longitude": 19.840029, "latitude": 45.236254, "isStop": false},{"longitude": 19.840265, "latitude": 45.235854, "isStop": false},{"longitude": 19.840731, "latitude": 45.23596, "isStop": false},{"longitude": 19.841273, "latitude": 45.236058, "isStop": false}], "follow":true}'
"""
@events.init_command_line_parser.add_listener
def _(parser):
    parser.add_argument("--data", type=json.loads)


@events.test_start.add_listener
def _(environment, **kw):
    data = environment.parsed_options.data
    global VEHICLE_ID, ROUTE, FOLLOW_ROUTE
    VEHICLE_ID = data["id"]
    FOLLOW_ROUTE = data["follow"]
    for waypoint in data["waypoints"]:
        coordinates = [waypoint["longitude"], waypoint["latitude"], waypoint["stop"]]
        ROUTE.append(coordinates)


class SimulateRide(HttpUser):
    fixed_count = 1
    pool_manager = PoolManager(maxsize=1, block=True)

    @task
    def set_location(self):
        global ROUTE, FOLLOW_ROUTE
        if not FOLLOW_ROUTE:
            new_route = []
            prev_waypoint = None
            for waypoint in ROUTE:
                if waypoint[2]:
                    if prev_waypoint is None:
                        prev_waypoint = waypoint
                    else:
                        url = "https://api.openrouteservice.org/v2/directions/driving-car"
                        url += "?api_key=5b3ce3597851110001cf6248e39388eebabc4b62a4c73f8387f68638"
                        url += "&start="
                        url += f"{prev_waypoint[0]},{prev_waypoint[1]}"
                        url += "&end="
                        url += f"{waypoint[0]},{waypoint[1]}"
                        response = self.client.get(url)
                        new_route = [*new_route,
                                     prev_waypoint,
                                     *response.json()["features"][0]["geometry"]["coordinates"],
                                     waypoint]
                        prev_waypoint = None
            ROUTE = new_route

        for waypoint in ROUTE:
            longitude = waypoint[0]
            latitude = waypoint[1]
            location = {"longitude": longitude, "latitude": latitude}
            request_body = {
                "id": VEHICLE_ID,
                "location": location
            }
            self.client.put("/vehicle/location", json=request_body)
            print(location)
            time.sleep(2)
        self.environment.runner.quit()
