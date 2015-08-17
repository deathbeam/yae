buttons = require "yae.constants.buttons"
keys = require "yae.constants.keys"
formats = require "yae.constants.formats"
wraps = require "yae.constants.wraps"
filters = require "yae.constants.filters"
blendmodes = require "yae.constants.blendmodes"
shapetypes = require "yae.constants.shapetypes"
aligns = require "yae.constants.aligns"

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
