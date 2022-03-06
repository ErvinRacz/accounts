-- liquibase formatted sql

-- changeset erivnracz:1646559462509-1
ALTER TABLE account
    ADD created_date TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE account
    ADD last_modified_date TIMESTAMP WITHOUT TIME ZONE;

-- changeset erivnracz:1646559462509-2
ALTER TABLE account
    ALTER COLUMN created_date SET NOT NULL;

-- changeset erivnracz:1646559462509-4
ALTER TABLE account
    ALTER COLUMN last_modified_date SET NOT NULL;

