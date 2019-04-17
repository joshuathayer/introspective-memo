clean:
	rm -Rf out

cider:
	clj -A:cider-cljs

repl:
	clj --main cljs.main --watch src --target nodejs --compile im.core -re node --repl
