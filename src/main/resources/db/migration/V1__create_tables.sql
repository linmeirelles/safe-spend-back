-- Tabela de Usuários
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Contas/Carteiras
CREATE TABLE accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    initial_balance DECIMAL(19, 2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_accounts_user_id ON accounts(user_id);

-- Tabela de Categorias
CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    icon VARCHAR(255),
    type VARCHAR(50) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_categories_user_id ON categories(user_id);
CREATE INDEX idx_categories_user_type ON categories(user_id, type);

-- Tabela de Cartões de Crédito
CREATE TABLE credit_cards (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    closing_day INTEGER NOT NULL CHECK (closing_day >= 1 AND closing_day <= 31),
    due_day INTEGER NOT NULL CHECK (due_day >= 1 AND due_day <= 31),
    limit_value DECIMAL(19, 2) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_credit_card_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_credit_cards_user_id ON credit_cards(user_id);

-- Tabela de Transações
CREATE TABLE transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    date DATE NOT NULL,
    paid BOOLEAN NOT NULL DEFAULT FALSE,
    type VARCHAR(50) NOT NULL,
    category_id UUID NOT NULL,
    account_id UUID,
    credit_card_id UUID,
    user_id UUID NOT NULL,
    installment_current INTEGER,
    installment_total INTEGER,
    CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE SET NULL,
    CONSTRAINT fk_transaction_credit_card FOREIGN KEY (credit_card_id) REFERENCES credit_cards(id) ON DELETE SET NULL,
    CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_category_id ON transactions(category_id);
CREATE INDEX idx_transactions_account_id ON transactions(account_id);
CREATE INDEX idx_transactions_credit_card_id ON transactions(credit_card_id);
CREATE INDEX idx_transactions_date ON transactions(date);
CREATE INDEX idx_transactions_user_date ON transactions(user_id, date);
