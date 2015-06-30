buttons = require "non.constants.buttons"
keys = require "non.constants.keys"
formats = require "non.constants.formats"
wraps = require "non.constants.wraps"
filters = require "non.constants.filters"
blendmodes = require "non.constants.blendmodes"
shapetypes = require "non.constants.shapetypes"
aligns = require "non.constants.aligns"

keycodes = {}
for k, v in pairs keys
  keycodes[v] = k

buttoncodes = {}
for k, v in pairs buttons
  buttoncodes[v] = k

formatcodes = {}
for k, v in pairs formats
  formatcodes[v] = k

wrapcodes = {}
for k, v in pairs wraps
  wrapcodes[v] = k

filtercodes = {}
for k, v in pairs filters
  filtercodes[v] = k

{
  :keys
  :keycodes
  :buttons
  :buttoncodes
  :formats
  :formatcodes
  :wraps
  :wrapcodes
  :filters
  :filtercodes
  :blendmodes
  :shapetypes
  :aligns
}