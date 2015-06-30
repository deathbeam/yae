buttons = require "non.internal.buttons"
keys = require "non.internal.keys"
formats = require "non.internal.formats"
wraps = require "non.internal.wraps"
filters = require "non.internal.filters"
blendmodes = require "non.internal.blendmodes"
shapetypes = require "non.internal.shapetypes"
aligns = require "non.internal.aligns"

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