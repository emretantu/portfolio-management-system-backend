-- Insert demo data for currency table
INSERT INTO currency (name) VALUES
                                ('USD'), ('EUR'), ('GBP'), ('TRY'), ('CAD'),
                                ('AUD'), ('NZD'), ('JPY'), ('CNY'), ('RUB');

-- Insert demo data for asset_type table
INSERT INTO asset_type (name) VALUES
                                  ('Stocks'), ('Bonds'), ('Funds'), ('Commodities'), ('Real Estate'),
                                  ('Derivatives'), ('Currencies'), ('Crypto'), ('Options'), ('Futures');

-- Insert demo data for asset table
INSERT INTO asset (name, currency_id, asset_type_id, user_id, status) VALUES
                                                                          ('Apple', 1, 1, 1, TRUE), ('Microsoft', 1, 1, 1, TRUE),
                                                                          ('Tesla', 1, 1, 2, TRUE), ('Amazon', 1, 1, 2, TRUE),
                                                                          ('Vanguard Total Stock Market ETF', 1, 3, 1, TRUE),
                                                                          ('iShares Core U.S. Aggregate Bond ETF', 1, 2, 2, TRUE),
                                                                          ('Gold', 2, 4, 1, TRUE), ('Silver', 2, 4, 2, TRUE),
                                                                          ('Vacation Home in Hawaii', 1, 5, 1, FALSE), ('Corn Futures', 3, 4, 2, TRUE);

-- Insert demo data for transaction_type table
INSERT INTO transaction_type (name) VALUES
                                        ('Deposit'), ('Withdrawal'), ('Buy'), ('Sell'), ('Income'),
                                        ('Expense'), ('Division'), ('Consolidation');

-- Insert demo data for portfolio table
INSERT INTO portfolio (user_id, name, currency_id, status) VALUES
                                                               (1, 'Retirement Portfolio', 1, TRUE),
                                                               (2, 'Short-term Savings', 1, TRUE),
                                                               (1, 'Real Estate', 1, TRUE),
                                                               (2, 'Commodities', 2, FALSE),
                                                               (1, 'Options Trading', 1, TRUE),
                                                               (2, 'Crypto Investments', 2, TRUE),
                                                               (1, 'Futures Trading', 1, FALSE),
                                                               (2, 'Forex Trading', 3, TRUE),
                                                               (1, 'Margin Account', 1, TRUE),
                                                               (2, 'Hedge Fund', 1, TRUE);

-- Insert demo data for share_transaction table
INSERT INTO share_transaction (portfolio_id, change_of_quantity, change_of_cost, time) VALUES
                                                                                           (1, 100, 1000, NOW()), (1, -50, -500, NOW()),
                                                                                           (2, 200, 2000, NOW()), (2, -100, -1000, NOW()),
                                                                                           (3, 1, 1000, NOW()), (3, -1, -5000, NOW()),
                                                                                           (4, 1000, 15000, NOW()), (4, -500, -7000, NOW()),
                                                                                           (5, 10, 5000, NOW()), (5, -5, -2000, NOW());

-- INSERT DEMO DATA FOR transaction TABLE
INSERT INTO transaction (portfolio_id, asset_id, transaction_type_id, change_of_quantity, change_of_main_cost, change_of_portfolio_cost, time)
VALUES
    (1, 1, 1, 10, 500, 5000, NOW()),
    (1, 2, 2, 5, 400, 2000, NOW()),
    (1, 3, 3, 2, 200, 400, NOW()),
    (2, 4, 1, 20, 1000, 20000, NOW()),
    (2, 5, 2, 10, 800, 8000, NOW()),
    (2, 6, 3, 3, 300, 600, NOW()),
    (3, 7, 1, 15, 750, 11250, NOW()),
    (3, 8, 2, 7, 560, 3920, NOW()),
    (3, 9, 3, 1, 100, 100, NOW()),
    (4, 10, 1, 25, 1250, 31250, NOW());

-- INSERT DEMO DATA FOR asset_track TABLE
INSERT INTO asset_track (asset_id, value, time)
VALUES
    (1, 100, NOW()),
    (1, 102, NOW() - INTERVAL '1 day'),
    (1, 98, NOW() - INTERVAL '2 day'),
    (1, 101, NOW() - INTERVAL '3 day'),
    (1, 99, NOW() - INTERVAL '4 day'),
    (2, 50, NOW()),
    (2, 52, NOW() - INTERVAL '1 day'),
    (2, 48, NOW() - INTERVAL '2 day'),
    (2, 51, NOW() - INTERVAL '3 day'),
    (2, 49, NOW() - INTERVAL '4 day');