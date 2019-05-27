SRCDIR=src
CLSDIR=classes

JC=javac
JFLAGS=-d $(CLSDIR)/ -sourcepath $(SRCDIR)
JARNAME=BGEditor

#################################################################

CLASSES = Descriptor \
	Background \
	BackgroundDescriptorView \
	EditorWindow

CLSFILES = $(addprefix $(CLSDIR)/, $(addsuffix .class, $(CLASSES)))

MAIN = EditorWindow

#################################################################

build: $(JARNAME).jar

$(JARNAME).jar: $(CLSFILES)
	jar cef $(MAIN) $@ -C $(CLSDIR) .
	@echo "Build successful!"

$(CLSDIR)/%.class: $(SRCDIR)/%.java
	@echo "Building " $@ "..."
	$(JC) $(JFLAGS) $<

clean:
	rm -f $(JARNAME).jar $(CLSDIR)/* 

