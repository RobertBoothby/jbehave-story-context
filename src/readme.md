jbehave-story-context
=====================
This project contains an extension to JBehave that I have re-created many times - the idea of a 'Story Context'. As
BDD is about creating human readable executable testing it is worth providing as many 'human' friendly behaviours
as possible.

One of the key ones for me is that human communication is very succinct; In a conversation once I have referred to
'London' or a concept relating to London I have established a context and do not need to keep on referring to it in
every sentence. e.g.

  I went to London last year. It was spring time in London when I went there last year. I found London very
  beautiful in the spring when I went there last year. I enjoyed travelling around beautiful London on foot in the
  spring when I went there last year... etc.

  or...

  I went to London last year. It was the spring. I found it very beautiful. I enjoyed travelling on foot...

This ability to establish a context is even more useful in testing as it reduces the amount of repetition in writing
the tests, reduces the likelihood of mistakes in the tests and massively improves the legibility.

In JBehave I have seen many de-facto attempts to create a context often by making the steps classes stateful by
adding attributes that were changed as the steps executed. While they have addressed the immediate need for a
context they are often inelegant as there is no reliable way to reset state between story runs and make it tough to
honour the Law of Demeter as there may be significant coupling between Steps classes if they need to share data.
Alternatively it can lead to the creation of massive Steps classes so that all the state data is shared in as few
places as possible.

This project is an attempt to create a story context in an elegant way that enables better ways of working with
JBehave.
