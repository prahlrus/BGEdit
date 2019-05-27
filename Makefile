SRCDIR=src/
BINDIR=target/com/stinja/birg

JC=javac
JFLAGS=-d $(BINDIR)/ -cp $(BINDIR)
JARNAME=BGEditor

#################################################################

APPCLASSES = ExpectationView \
	LatexReader \
	EditorWindow

ENTCLASSES = Descriptor \
	Background \
	ExpectationType \
	Expectation 

CLSFILES = $(addsuffix .class, $(addprefix $(BINDIR)/Entities/, $(ENTCLASSES)) $(addprefix $(BINDIR)/Application/, $(APPCLASSES)))

MAIN = com.stinja.birg.Application.EditorWindow

#################################################################

build: $(JARNAME).jar

$(JARNAME).jar: $(CLSFILES)
	jar cef $(MAIN) $@ -C $(BINDIR) .
	@echo "Build successful!"

$(BINDIR)/%.class: $(SRCDIR)/%.java
	@echo "Building " $@ "..."
	$(JC) $(JFLAGS) $<

clean:
	rm -rf $(JARNAME).jar $(BINDIR)/* 
