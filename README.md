# Yield Curve Interpolation Project

## Overview
This project includes implementations of a yield curve interpolation algorithm in Python and Java. The purpose of the algorithm is to calculate interpolated or extrapolated yield rates (bid, ask, or mid) for a given date.

## Scripts Description

### YieldCurve.java
This file contains the Java implementation of the yield curve interpolation algorithm. It provides methods to:
- Retrieve bid, ask, or mid rates for a given date.
- Interpolate rates between provided dates.
- Handle errors for dates outside the provided range.

### YieldCurveApp.java
This script demonstrates the usage of `YieldCurve.java` by computing yield rates based on user inputs. The script takes parameters such as date and rate type ('ask', 'bid', or 'mid').

### YieldCurveTest.java
This file contains JUnit tests for `YieldCurve.java`. The tests cover scenarios including:
- Exact match for bid and ask rates.
- Interpolation of rates between provided dates.
- Handling exceptions for dates outside the provided range.

## Solution Approach

### Algorithm Description

#### Initialization
- Start by parsing the input date \( d \) and identifying whether to calculate the bid, ask, or mid rate.

#### Check Date Range
- If \( d \) is before the earliest date in the yield curve data, indicate that the date is out of the valid range.
- If \( d \) is after the latest date in the yield curve data:
  - For bid rate, use the last available bid rate.
  - For ask rate, use the last available ask rate.
  - For mid rate, use the last available mid rate.

#### Interpolation
- Iterate through the list of dates and corresponding rates.
- Find the interval where \( d \) falls between two dates.
- Calculate an interpolation factor based on how close \( d \) is to these dates.
- Interpolate the rate for the specified rate type (bid, ask, or mid) based on the interpolation using linear interpolation method.

#### Error Handling
- If \( d \) does not fall within the range of dates provided and cannot be extrapolated, indicate that the date is out of the valid range.

This algorithm ensures that the input date is valid, interpolates rates if the date falls within the provided range, and handles errors for dates outside that range by either providing the nearest available rate or indicating an out-of-range error.


## Usage
To run the application and compute yield rates, use `YieldCurveApp.java` with appropriate inputs.

To run the tests and validate the algorithm's functionality, use `YieldCurveTest.java` with JUnit set up in your development environment.

