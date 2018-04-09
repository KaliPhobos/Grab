# Grab
Intelligent USB grabber
GRAB uses is basically a USB grabber. Once you run it, it tries to copy any files from a removable device - a basic tool
you would use to quickly get an exact copy of a removable drive.
But unlike others GRAB uses various factors (like filet type, file size, name, ...) to give a unique priority to each file.
That way even if the copy process should be interrupted you should end up with the most "interesting" files first:
Documents, pictures, videos, ...

Currently affecting the order in which files are copied:
- File size: The closer to the sweetspot the higher the priority
- file type: The file ending heavily influences the proprity (who wants .dll files?)
- file quantity: There is 100 videos but only 1 picture? Grab the picture first!
