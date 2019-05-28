SRCDIR=src
BINDIR=target
PACKDIR=com/stinja/birg

JC=javac
JFLAGS=-d $(BINDIR)/ -cp $(BINDIR)
JARNAME=BGEditor

#################################################################

APPCLASSES = $(addprefix Application/, \
	ExpectationView \
	LatexReader \
	EditorWindow )

ENTCLASSES = $(addprefix Entities/, \
	Descriptor \
	Background \
	ExpectationType \
	Expectation \
	Observer \
	DataModel )

CLSFILES = $(addprefix $(BINDIR)/$(PACKDIR)/, $(addsuffix .class, $(ENTCLASSES) $(APPCLASSES)))

MAIN = com.stinja.birg.Application.EditorWindow

#################################################################

build: $(JARNAME).jar

$(JARNAME).jar: $(CLSFILES)
	jar cef $(MAIN) $@ -C $(BINDIR) .
	@echo "Build successful!"

$(BINDIR)/$(PACKDIR)/%.class: $(SRCDIR)/%.java
	@echo "Building " $@ "..."
	$(JC) $(JFLAGS) $<

clean:
	rm -rf $(JARNAME).jar $(BINDIR)/* 
