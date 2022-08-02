from itertools import chain
import random


class Card:
    __slot__ = "suit value".split()

    def __init__(self, suit, value) -> None:
        self.suit = suit
        self.value = value

    def __repr__(self):
        return " of ".join((self.value, self.suit))


class Deck:
    def __init__(self) -> None:
        self.cards = [
            Card(s, v)
            for s in ["Spades", "Clubs", "Hearts", "Diamonds"]
            for v in "A 2 3 4 5 6 7 8 9 10 J Q K".split(" ")
        ]

    def shuffule(self):
        if len(self.cards) > 1:
            random.shuffle(self.cards)

    def deal(self):
        if len(self.cards) > 1:
            return self.cards.pop(0)


class Hand:
    def __init__(self, dealer=False) -> None:
        self.dealer = dealer
        self.cards = []
        self.value = 0

    def add_card(self, card):
        self.cards.append(card)

    def calculate_value(self):
        self.value = 0
        has_ace = False
        for card in self.cards:
            if card.value.isnumeric():
                self.value += int(card.value)
            elif card.value == "A":
                has_ace = True
                self.value += 11
            else:
                self.value += 10
        if has_ace and self.value > 21:
            self.value -= 10

    def get_value(self):
        self.calculate_value()
        return self.value

    def __repr__(self) -> str:
        if self.dealer:
            return f"hidden: {self.cards[0]}"
        else:
            return f"""cards: {', '.join(map(repr, self.cards))} 
value: {self.get_value()}"""


class Game:
    def __init__(self) -> None:
        playing = True

        while playing:
            self.deck = Deck()
            self.deck.shuffule()

            self.player_hand = Hand()
            self.dealer_hand = Hand(True)

            for i in range(2):
                self.player_hand.add_card(self.deck.deal())
                self.dealer_hand.add_card(self.deck.deal())

            print("Your hand is:")
            print(self.player_hand)
            print()
            print("Dealer's hand is:")
            print(self.dealer_hand)

            game_over = False
            while not game_over:
                player_has_blackjack, dealer_has_blackjack = self.check_for_blackjack()
                if player_has_blackjack or dealer_has_blackjack:
                    game_over = True
                    if player_has_blackjack and dealer_has_blackjack:
                        print("Draw")
                    elif player_has_blackjack:
                        print("You win")
                    elif dealer_has_blackjack:
                        print("Dealder win")
                    break

                choice = input("Please choose [Hit / Stick]").lower()
                while choice not in {"h", "s", "hit", "stick"}:
                    choice = input("Please choose [Hit / Stick]").lower()
                if choice in {"h", "hit"}:
                    self.player_hand.add_card(self.deck.deal())
                    print(self.player_hand)
                    if self.player_hand.get_value() > 21:
                        print("You lost")
                        winner = self.dealer_hand
                    else:
                        winner = self.player_hand
                else:
                    print(
                        "Final Results:", self.player_hand, self.dealer_hand, sep="\n"
                    )
                    if self.player_hand.get_value() > self.dealer_hand.get_value():
                        print("You win")
                        winner = self.player_hand
                    elif self.player_hand.get_value() == self.dealer_hand.get_value():
                        print("tie")
                    else:
                        print("Dealer win")
                        winner = self.dealer_hand

            again = input("Play Again? [Y/N] ")
            while again.lower() not in ["y", "n"]:
                again = input("Please enter Y or N ")
            if again.lower() == "n":
                print("Thanks for playing!")
                playing = False
            else:
                winner = None

    def check_for_blackjack(self):
        return [p.get_value() == 21 for p in (self.player_hand, self.dealer_hand)]


Game()
