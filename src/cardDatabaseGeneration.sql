--CREATE DATABASE users;

CREATE TABLE cards (
	card_id SERIAL PRIMARY KEY,
	card_name VARCHAR(50) NOT NULL,
	mana_cost INTEGER NOT NULL,
	description VARCHAR(150),
	rarity VARCHAR(15) NOT NULL,
	card_type VARCHAR(15) NOT NULL,
	class_type VARCHAR(15) NOT NULL
);

CREATE TABLE minions(
	card_id INTEGER REFERENCES cards(card_id) PRIMARY KEY,
	attack INTEGER NOT NULL,
	health INTEGER NOT NULL
);

CREATE TABLE weapons(
	card_id INTEGER REFERENCES cards(card_id) PRIMARY KEY,
	attack INTEGER NOT NULL,
	charges INTEGER NOT NULL
);

DROP TABLE IF EXISTS cards CASCADE;

INSERT INTO cards (card_name, mana_cost, description, rarity, card_type, class_type)
	VALUES('Abomination', 5, 'Taunt. Deathrattle: deal 2 damage to all characters.', 'rare', 'minion', 'neutral'),
	('Abusive Sergeant', 1, 'Battlecry: Give a minion +2 Attack this turn.', 'common', 'minion', 'neutral'),
	('Acolyte of Pain', 3, 'Whenever this minion tkaes damage, draw a card.', 'common', 'minion', 'neutral'),
	('Adrenaline Rush', 1, 'Draw a card. Combo: Draw 2 cards instead.', 'epic', 'spell', 'neutral'),
	('Al''Alkir the Windlord', 8, 'Windfury, Charge, Divine Shield, Taunt.', 'legendary', 'minion', 'shaman'),
	('Alarm-o-Bot', 3, 'At the start of your turn, swap tis minion with a random one in your hand.', 'rare', 'minion', 'neutral'),
	('Aldor Peacekeeper', 3, 'Battlecry: Change an enemy minion''s attack to 1.', 'rare', 'minion', 'paladin'),
	('Alexstrasza', 9, 'Battlecry: Set a hero''s remaining health to 15.', 'legendary', 'minion', 'neutral'),
	('Amani Berserker', 2, 'Enrage: +3 Attack.', 'common', 'minion', 'neutral'),
	('Ancestral Spirit', 2, 'Choose a minion. When that minion is destroyed, return it to the battlefield.', 'rare', 'spell', 'shaman'),
	('Ancient Brewmaster', 4, 'Battlecry: Return a friendly minion from the battlefield to your hand.', 'common', 'minion', 'neutral'),
	('Ancient Mage', 4, 'Battlecry: Give adjacent minions Spell Damage + 1.', 'rare', 'minion', 'neutral'),
	('Ancient of Lore', 7, 'Choose One - Draw 2 cards; or Restore 5 Health.', 'epic', 'minion', 'druid'),
	('Ancient of War', 7, 'Choose One - Taunt and +5 Health; or +5 Attack.', 'epic', 'minion', 'druid'),
	('Ancient Secrets', 0, 'Restore 5 Health.', 'common', 'spell', 'druid'),
	('Ancient Teachings', 0, 'Draw 2 cards.', 'common', 'spell', 'druid'),
	('Ancient Watcher', 2, 'Can''t Attack.', 'rare', 'minion', 'neutral'),
	('Angry Chicken', 1, 'Enrage +5 Attack.', 'rare', 'minion', 'neutral'),
	('Arathi Weaponsmith', 4, 'Battlecry: Equip a 2/2 weapon.', 'common', 'minion', 'warrior'),
	('Arcane Golem', 3, 'Charge. Battlecry: Give your opponent a Mana Crystal.', 'rare', 'minion', 'neutral'),
	('Archmage Antonidas', 7, 'Whenever you cast a spell, put a ''Fireball'' spell into your hand.', 'legendary', 'minion', 'mage'),
	('Argent Commander', 6, 'Charge, Divine Shield.', 'rare', 'minion', 'neutral'),
	('Argent Protector', 2, 'Battlecry: Give a friendly minion Divine Shield.', 'common', 'minion', 'paladin'),
	('Argent Squire', 1, 'Divine Shield.', 'common', 'minion', 'neutral'),
	('Armorsmith', 2, 'Whenever a friendly minion takes damage, gain 1 Armor.', 'rare', 'minion', 'warrior'),
	('Ashbringer', 5, '', 'legendary', 'weapon', 'paladin'),
	('Auchenai Soulpriest', 4, 'Your cards and powers that restore Health now deal damage instead.', 'rare', 'minion', 'priest'),
	('Avenging Wrath', 6, 'Deal 8 damage randomly split among enemy characters.', 'epic', 'spell', 'paladin'),
	('Azure Drake', 5, 'Spell Damage +1. Battlecry: Draw a card.', 'rare', 'minion', 'neutral'),
	('Baine Bloodhoof', 4, '', 'legendary', 'minion', 'neutral'),
	('Bane of Doom', 5, 'Deal 2 damage to a character. If that kills it, summon a random Demon.', 'epic', 'spell', 'warlock'),
	('Baron Geddon', 7, 'At the end of your turn, deal 2 damage to ALL other characters.', 'legendary', 'minion', 'neutral'),
	('Battle Rage', 2, 'Draw a card for each damaged friendly character.', 'common', 'spell', 'warrior'),
	('Bear Form', 0, '+2 Health and Taunt.', 'common', 'spell', 'druid'),
	('Bestial Wrath', 1, 'Give a Beast +2 Attack and Immune this turn.', 'epic', 'spell', 'hunter'),
	('Betrayal', 2, 'An enemy minion deals its damage to the minions next to it.', 'common', 'spell', 'rogue'),
	('Big Game Hunter', 3, 'Battlecry: Destroy a minion with an Attack of 7 or more.', 'epic', 'minion', 'neutral'),
	('Bite', 4, 'Give your hero +4 Attack this turn and 4 Armor.', 'rare', 'spell', 'druid'),
	('Blade Flurry', 2, 'Destroy your weapon and deal its damage to all enemies.', 'rare', 'spell', 'rogue'),
	('Blessed Champion', 5, 'Double a minion''s Attack.', 'rare', 'spell', 'paladin'),
	('Blessing of Wisdom', 1, 'Choose a minion. Whenever it attacks, draw a card.', 'common', 'spell', 'paladin'),
	('Blizzard', 6, 'Deal 2 damage to all enemy minions and Freeze them.', 'rare', 'spell', 'mage'),
	('Blood Fury', 3, '', 'common', 'weapon', 'warlock'),
	('Blood Imp', 1, 'Stealth. At the end of your turn, give another random friendly minion +1 Health.', 'common', 'minion', 'warlock'),
	('Blood Knight', 3, 'Battlecry: All minions lose Divine Shield. Gain +3/+3 for each Shield lost.', 'epic', 'minion', 'neutral'),
	('Bloodmage Thalnos', 2, 'Spell Damage +1. Deathrattle: Draw a card.', 'legendary', 'minion', 'neutral'),
	('Bloodsail Corsair', 1, 'Battlecry: Remove 1 Durability from your opponent''s weapon.', 'rare', 'minion', 'neutral'),
	('Bloodsail Raider', 2, 'Battlecry: Gain Attack equal to the Attack of your weapon.', 'common', 'minion', 'neutral'),
	('Boar', 1, '', 'common', 'minion', 'neutral'),
	('Brawl', 5, 'Destroy all minions except one.(chosen randomly)', 'epic', 'spell', 'warrior'),
	('Brewmaster', 4, '', 'common', 'minion', 'neutral');
	--('', 1, '.', '', '', ''),

INSERT INTO minions (card_id, attack, health)
	VALUES (1, 4, 4),
	(2,2,1),
	(3,1,3),
	(5,3,5),
	(6,0,3),
	(7,3,3),
	(8,8,8),
	(9,2,3),
	(11,5,4),
	(12,2,5),
	(13,5,5),
	(14,5,5),
	(17,4,5),
	(18,1,1),
	(19,3,3),
	(20,4,2),
	(21,5,7),
	(22,4,2),
	(23,2,2),
	(24,1,1),
	(25,1,4),
	(27,3,5),
	(29,4,4),
	(30,4,5),
	(32,7,5),
	(37,4,2),
	(44,0,1),
	(45,3,3),
	(46,1,1),
	(47,1,2),
	(48,2,3),
	(49,1,1),
	(51,4,4);

INSERT INTO weapons (card_id, attack, charges)
	VALUES (26,5,3),
	(43,3,8);

SELECT c.card_id, c.card_name,
	c.mana_cost, c.description, c.rarity, c.card_type,
	c.class_type, m.attack, m.health, w.charges
FROM cards c 
	FULL JOIN minions m ON (c.card_id = m.card_id)
	FULL JOIN weapons w ON (c.card_id = w.card_id);
	
	
	