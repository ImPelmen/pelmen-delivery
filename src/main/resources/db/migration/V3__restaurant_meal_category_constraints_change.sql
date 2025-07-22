ALTER TABLE IF EXISTS meal
    DROP CONSTRAINT IF EXISTS fk_meal_restaurant;
ALTER TABLE IF EXISTS meal
    DROP COLUMN IF EXISTS restaurant_id;

ALTER TABLE IF EXISTS meal_category
    ADD COLUMN IF NOT EXISTS restaurant_id BIGINT NOT NULL;
ALTER TABLE IF EXISTS meal_category
    ADD CONSTRAINT fk_meal_category_restaurant
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id);