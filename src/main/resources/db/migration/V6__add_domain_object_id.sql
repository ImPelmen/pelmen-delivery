ALTER TABLE IF EXISTS domain_order
    ADD COLUMN IF NOT EXISTS domain_object_id BIGINT;
ALTER TABLE IF EXISTS domain_order
    ADD CONSTRAINT fk_domain_order_domain_object
    FOREIGN KEY (domain_object_id) REFERENCES domain_object (id);