function extgcd(a, b) {
    if (b == 0) return [a, BigInt(1), BigInt(0)];
    var d = extgcd(b, a % b);
    return [d[0], d[2], d[1] - a / b * d[2]];
}

function expmod(base, exp, mod) {
    if (exp == 0) return BigInt(1);
    if (exp % BigInt(2) == 0) {
        return expmod(base, (exp / BigInt(2)), mod) ** BigInt(2) % mod;
    } else {
        return (base * expmod(base, (exp - BigInt(1)), mod)) % mod;
    }
}

class RSA {
    constructor(p, q) {
        p = BigInt(p);
        q = BigInt(q);

        this.n = p * q;
        var phi = (p - BigInt(1)) * (q - BigInt(1));
        this.e = BigInt(0x10001);
        var d = extgcd(this.e, phi)[1];
        if (d < 0) d += phi;
        this.d = d;
    }

    encrypt(msg) {
        const result = [];
        const cpts = Array.from(msg).map(x => BigInt(x.codePointAt(0)));
        for (let idx = 0; idx < cpts.length; idx++) {
            result.push(cpts[idx] ** this.e % this.n);
        }
        return result;
    }

    decrypt(encrypted) {
        const msg = [];
        for (let idx = 0; idx < encrypted.length; idx++) {
            msg.push(expmod(encrypted[idx], this.d, this.n));
        }
        return String.fromCodePoint(...msg.map(x => Number(x)));
    }

    public_key() {
        return [this.n, this.e];
    }

    private_key() {
        return [this.n, this.d];
    }
}

// Get some from here! https://bigprimes.org/ 100 digits woud be sufficient
cpk = new RSA('41','11');

var msg = `
Because of serious arguments that my mom and my step father had, I won't
be able to come to the park today with you all. I shall say sorry sincerely
once again here.

TLDR: My mom and my father was on the edge of divorce, and eventully, let my
mom obtain equal position as my father had. Let's celebrate! (But it start
really really bad, if you take a look at quoted part, explaining where the fear
comes from. I haven't invented this RSA js thing that earlly, which is also a good
thing! you don't need to worry about those shit with me for 4 days!).

For 5 days in a row, my life had filled with tear and sigh. It started around
4-5th July, and everyday is like that, besides that I only involved to
extremity for one day (If you still remember, on whatever day, I stay up very
late to talk to my stepfather.)

At the beginning, my father and my mother acted like as if they would divorce
in one day.  
- The termination of all the unhappiness and torturements that my
father had imposed to us (my mom and me), shall be gone. We shall enjoy the
freedom of living in America. 

> I shall explain a little bit about why my mother and my father didn't
> divorce. As you know, I am fundamentally different compare to Sean or You -- I
> am only resident, but not citizen(Sssh!). The way that we obtain green card
> and become US citizen, relying on my stepfather's sponsorship. Yet, he use
> this as a weapon and turn our gratitude to resentment that can't explode.

- Until recently, my mom had summed up the confidence to divorce with him
because she joined Microsoft. (Before that, if she do so, then the time that I
would have in America shall start to count down. There is a very small
possibility that the U.S. goverment would feel like that the marriage between
my mom and my stepfather is not bona fide, but rather, a tool for us to live
here. And my mom don't have a powerful enough company to support her and give
her green card, then there would not be any Plan B.). 

If I send those stuff several days ago, I mean, the end of world would come.
Yet it turns out that my stepfather fears to lose my mom (as an obedient
servant?). 

It turns out, that all the threating he have used (i.e. let's divorce
today and you guys enjoy the last week in America), are simply flaunting of
his power (actually, he claims that if my mom send him 500 dollar and 50
extra, he will make divorce immediately in front of me and my mom on the start
of this event. Now he start to regret and act like as if my mom didn't give
him any money at all, ha~) and is his only method to make my mom do what he
what she to do. Soon he realized that this no longer work he starts to cry,
sad, do all kind of trash try to let my mom forgave him (because my mom acts
like a obedient servant for most of time for free). Soon, he annoyed my mom
and she start of cry out of mixture of fear (i.e., if real domestic violence
shall happen now), happy? (i.e., realization of his trick), and sad. That kind
of stuff continues for several day. Every night they will talk about it, and 
a contiuation of cry.

Finally, my mom start to forgive him (not that definite about divorce) and my
father start to rectify his behavior (can you believe that? he start to go out
of shopping with my mom and me in such a obedient, educated, and polite manner
that non of us has ever seen). So nothing bad really happend besides 4-5 days
of mental torture to listen two people crying. And I believe my mom would still
argue with him within few days, since, he is 60 and we can't expect him to change
himself anyway...) By all mean, this family revived again!

OK, that's a loving ending. Thanks for your patience. But let me ask you some
questions (use your ethics and general sense. I want to see if the maltreatments
that we had imposed to each other(we all claim each othe maltearted us), is 
considered that bad.):

- Use Chinese to speak. (and he starts to inspect everything) 
- Not talking to family members. (since he just read news articles from his
phone and act like as if he know more IT than my mom)
- Use a detective to inspect if someone else betray.
- Yelling and speaking shit (i.e., f**k) while someone else is in meeting with
their coworker.
`

var fs = require('fs')

var encrypt = cpk.encrypt(msg)
var file = fs.createWriteStream('encrypted.txt')
encrypt.forEach(v => file.write(v.toString() + "\n"))
file.end()

fs.readFile('encrypted.txt', (err, data) => {
	if(err) throw err;
	data = data.toString().split('\n').map(x => BigInt(x))
	console.log(cpk.decrypt(data))
});


