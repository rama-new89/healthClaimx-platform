# Zone-Based Segregation Implementation Guide

## Overview
This document describes the zone-based segregation implementation for the healthClaimx platform. Zone-based segregation ensures data isolation across different geographic regions or organizational zones.

## Architecture

### Zones Supported
- NORTH
- SOUTH
- EAST
- WEST
- CENTRAL
- NORTHEAST
- NORTHWEST
- SOUTHEAST
- SOUTHWEST

## Implementation Details

### 1. Database Schema Changes

#### User Entity
- Added `zone` field (String, NOT NULL)
- Users are now segregated by zone
- A user can only access resources within their assigned zone

#### Claim Entity
- Added `zone` field (String, NOT NULL)
- Claims are segregated by zone
- Only users from the same zone can access zone-specific claims

### 2. Repository Layer

#### UserRepository
- `findByUsernameAndZone(username, zone)`: Find user by username within a specific zone
- `findAllByZone(zone)`: Get all users in a zone
- `findByIdAndZone(id, zone)`: Get user by ID within a zone

#### ClaimRepository
- `findAllByZone(zone)`: Get all claims in a zone
- `findByIdAndZone(id, zone)`: Get specific claim within a zone
- `findByUserIdAndZone(userId, zone)`: Get claims for a user within a zone
- `findByStatusAndZone(status, zone)`: Get claims by status within a zone

### 3. Service Layer

#### AuthService
- Zone validation during user login
- Zone assignment during user registration
- Zone-based user retrieval

#### ClaimService
- Zone validation during claim creation
- Zone-based claim queries
- Zone-specific claim status updates

### 4. Controller Endpoints

#### Authentication API (/api/auth)
- `POST /login`: Login with zone (required in request body)
- `POST /register`: Register user with zone assignment
- `GET /user/{userId}/{zone}`: Retrieve user by ID and zone

#### Claims API (/api/claims)
- `POST`: Create claim (zone required in request body)
- `GET`: Get all claims (global)
- `GET /zone/{zone}`: Get all claims in a specific zone
- `GET /{claimId}/zone/{zone}`: Get specific claim in zone
- `GET /user/{userId}/zone/{zone}`: Get user's claims in zone
- `GET /status/{status}/zone/{zone}`: Get claims by status in zone
- `PUT /{claimId}/zone/{zone}/status/{status}`: Update claim status within zone

### 5. Security

#### Zone Validation Filter
- Validates zone from request headers (`X-Zone`)
- Ensures requests are associated with a zone

#### Zone Configuration
- Central configuration for valid zones
- Validation helper methods for zone checks

## Usage Examples

### User Login with Zone
```json
POST /api/auth/login
{
  "username": "john_doe",
  "password": "password123",
  "zone": "NORTH"
}
```

Response:
```json
{
  "token": "jwt_token_here",
  "username": "john_doe",
  "zone": "NORTH",
  "userId": 1
}
```

### Create Claim in Zone
```json
POST /api/claims
{
  "userID": 1,
  "amount": 5000.00,
  "zone": "NORTH"
}
```

### Get Claims by Zone
```
GET /api/claims/zone/NORTH
```

### Get Claims by User and Zone
```
GET /api/claims/user/1/zone/NORTH
```

### Update Claim Status in Zone
```
PUT /api/claims/1/zone/NORTH/status/APPROVED
```

## Database Migration

To implement zone-based segregation, execute the following SQL migrations:

### Auth Service Database
```sql
ALTER TABLE users ADD COLUMN zone VARCHAR(50) NOT NULL DEFAULT 'CENTRAL';
CREATE INDEX idx_user_zone ON users(zone);
CREATE INDEX idx_user_username_zone ON users(username, zone);
```

### Claim Service Database
```sql
ALTER TABLE claims ADD COLUMN zone VARCHAR(50) NOT NULL DEFAULT 'CENTRAL';
CREATE INDEX idx_claim_zone ON claims(zone);
CREATE INDEX idx_claim_user_zone ON claims(user_id, zone);
CREATE INDEX idx_claim_status_zone ON claims(status, zone);
```

## Data Isolation Guarantees

1. **User Isolation**: Users can only be queried within their assigned zone
2. **Claim Isolation**: Claims are strictly segregated by zone
3. **Cross-Zone Prevention**: The repository layer prevents cross-zone data access
4. **Service Layer Validation**: All service methods validate and enforce zone constraints

## Best Practices

1. Always include the zone parameter when querying user or claim data
2. Validate zone values against the predefined set
3. Use the zone-specific repository methods instead of generic findAll()
4. Include zone information in API request headers: `X-Zone: NORTH`
5. Log zone information for audit purposes
6. Never expose cross-zone data in any API response

## Future Enhancements

- Implement role-based access control (RBAC) with zone awareness
- Add audit logging for zone-based operations
- Implement zone-specific data retention policies
- Add zone hierarchy support (regional zones containing sub-zones)
- Implement cross-zone approval workflows for specific claim types
