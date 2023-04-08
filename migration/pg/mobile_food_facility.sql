create table mobile_food_facility (
    id serial primary key,
    applicant text,
    facility_type text,
    cnn integer,
    location_description text,
    address text,
    bloc_lot text,
    block text,
    lot text,
    permit text,
    status text,
    food_items text,
    x integer,
    y integer,
    latitude numeric,
    longitude numeric,
    schedule text,
    days_hours text,
    noi_sent text,
    approved text,
    received text,
    prior_permit integer,
    expiration_date text,
    location text,
    fire_prevention_districts text,
    police_districts text,
    supervisor_districts text,
    zip_codes text,
    neighborhoods text

);