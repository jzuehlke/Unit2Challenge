In this challenge, I decided to use the TreeMap collection class to figure out 
the total number of dates occurred by month.  I used TreeMap because I only needed 
to use each date's month name, which I could use as the TreeMap key.  I felt an 
ArrayList would be overkill, and a HashMap doesn't have the automatic sorting of keys 
that TreeMap has.  The only downside is the months are stored alphabetically instead 
of by their normal order, but it is still more organization than if I used HashMap.