Command line .epb unpacker

Example use:

java -jar egsbp.jar examplefile.epb

This will create two new files:
  examplefile.epb.meta
  examplefile.epb.data

The .meta file will exactly match a prefix of the original .epb file.

The .data file will match the unzipped content which follows that prefix
