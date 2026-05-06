## Backend Endpoints

### Spring Actuator

    GET /actuator/*

### Advisors

    GET /api/advisors

    POST /api/advisors

    PUT /api/advisors

    DELETE /api/advisors/{advisorId}

    GET /api/advisors/{advisorId}

    POST /api/advisors/{advisorId}/clients/{clientId}

    DELETE /api/advisors/{advisorId}/clients/{clientId}

    GET /api/advisors/specialization/{specialization}

    GET /api/advisors/user/{userId}

### Clients

    GET /api/clientrecords

    POST /api/clientrecords

    GET /api/clientrecords/{id}

    PUT /api/clientrecords/{id}

    DELETE /api/clientrecords/{id}

### Goals

    GET /api/goal

    POST /api/goal

    GET /api/goal/{id}

    PUT /api/goal/{id}

    DELETE /api/goal/{id}

### Users

    PUT /api/register